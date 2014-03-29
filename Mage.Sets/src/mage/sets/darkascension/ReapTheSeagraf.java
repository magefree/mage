package mage.sets.darkascension;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.game.permanent.token.ZombieToken;

/**
 * @author Loki
 */
public class ReapTheSeagraf extends CardImpl<ReapTheSeagraf> {

    public ReapTheSeagraf(UUID ownerId) {
        super(ownerId, 72, "Reap the Seagraf", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "DKA";

        this.color.setBlack(true);

        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken("ISD")));
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{4}{U}"), TimingRule.SORCERY));
    }

    public ReapTheSeagraf(final ReapTheSeagraf card) {
        super(card);
    }

    @Override
    public ReapTheSeagraf copy() {
        return new ReapTheSeagraf(this);
    }
}
