import matplotlib.pyplot as plt

# Liang-Barsky clipping algorithm
def liang_barsky_clip(x1, y1, x2, y2, xmin, ymin, xmax, ymax):
    p = [- (x2 - x1), (x2 - x1), - (y2 - y1), (y2 - y1)]
    q = [x1 - xmin, xmax - x1, y1 - ymin, ymax - y1]

    u1, u2 = 0, 1
    for i in range(4):
        if p[i] < 0:
            u1 = max(u1, q[i] / p[i])
        elif p[i] > 0:
            u2 = min(u2, q[i] / p[i])
        elif q[i] < 0:
            return None

    if u1 > u2:
        return None

    nx1 = x1 + u1 * (x2 - x1)
    ny1 = y1 + u1 * (y2 - y1)
    nx2 = x1 + u2 * (x2 - x1)
    ny2 = y1 + u2 * (y2 - y1)

    return [(nx1, ny1), (nx2, ny2)]

# Function to draw the clipped lines and clipping window
def draw_clipping(x1, y1, x2, y2, xmin, ymin, xmax, ymax, name):
    fig, ax = plt.subplots()
    # Draw the clipping window
    ax.plot([xmin, xmax, xmax, xmin, xmin], [ymin, ymin, ymax, ymax, ymin], 'k-', label='Clipping Window')
    # Draw the original line
    ax.plot([x1, x2], [y1, y2], 'r--', label='Original Line')
    
    # Liang-Barsky Clipping
    lb_result = liang_barsky_clip(x1, y1, x2, y2, xmin, ymin, xmax, ymax)
    if lb_result:
        lb_clipped = list(zip(*lb_result))
        ax.plot(lb_clipped[0], lb_clipped[1], 'g-', label='Liang-Barsky Clipped Line')
    
    # Annotate
    ax.annotate(name, (x1, y1), textcoords="offset points", xytext=(0,10), ha='center')
    ax.annotate(name, (x2, y2), textcoords="offset points", xytext=(0,10), ha='center')

    ax.legend()
    ax.set_xlim(min(x1, x2, xmin) - 10, max(x1, x2, xmax) + 10)
    ax.set_ylim(min(y1, y2, ymin) - 10, max(y1, y2, ymax) + 10)
    plt.gca().set_aspect('equal', adjustable='box')
    plt.show()

# Main function
def main():
    print("Enter coordinates of the line endpoints (x1, y1, x2, y2) from keyboard:")
    x1, y1 = map(float, input("Enter (x1, y1): ").split())
    x2, y2 = map(float, input("Enter (x2, y2): ").split())
    
    print("Enter the clipping window boundaries (xmin, ymin, xmax, ymax):")
    xmin, ymin, xmax, ymax = map(float, input("Enter (xmin, ymin, xmax, ymax): ").split())
    
    print(f"Original line: ({x1}, {y1}) to ({x2}, {y2})")
    print(f"Clipping window: ({xmin}, {ymin}) to ({xmax}, {ymax})")
    
    # Text output of intersection points
    print("Text output of intersection points:")
    result_lb = liang_barsky_clip(x1, y1, x2, y2, xmin, ymin, xmax, ymax)
    
    if result_lb:
        print(f"Liang-Barsky Clipped Line: {result_lb}")
    else:
        print("Line is outside the clipping window using Liang-Barsky algorithm")
    
    # Graphical output
    draw_clipping(x1, y1, x2, y2, xmin, ymin, xmax, ymax, 'Line')

if __name__ == "__main__":
    main()
