package mage.sets.darkascension;

import mage.Constants;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author Loki
 */
public class ReapTheSeagraf extends CardImpl<ReapTheSeagraf> {

    public ReapTheSeagraf(UUID ownerId) {
        super(ownerId, 72, "Reap the Seagraf", Constants.Rarity.COMMON, new Constants.CardType[]{Constants.CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "DKA";

        this.color.setBlack(true);

        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{4}{U}"), Constants.TimingRule.SORCERY));
    }

    public ReapTheSeagraf(final ReapTheSeagraf card) {
        super(card);
    }

    @Override
    public ReapTheSeagraf copy() {
        return new ReapTheSeagraf(this);
    }
}
