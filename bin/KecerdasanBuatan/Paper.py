import pandas as pd
from textblob import TextBlob
import matplotlib.pyplot as plt


df = pd.read_csv("C:/Kuliah/Semester 4/Strukdat/src/KecerdasanBuatan/testimoni.csv")


print(df.columns)


polarity_score = []

for text in df['text']:

    blob = TextBlob(text)
  
    for sentence in blob.sentences:
        
        if sentence.sentiment.polarity > 0.0:
            polarity_score.append("positive")
        elif sentence.sentiment.polarity < 0.0:
            polarity_score.append("negative")
        else:
            polarity_score.append("neutral")

df['sentiment'] = polarity_score

df.to_csv("nama_file_dengan_sentimen.csv", index=False)

sentiment_counts = df['sentiment'].value_counts()
sentiment_counts.plot(kind='bar')
plt.title('Sentimen pada Data Testimoni')
plt.xlabel('Sentimen')
plt.ylabel('Jumlah')
plt.xticks(rotation=0)
plt.tight_layout()

plt.show()
