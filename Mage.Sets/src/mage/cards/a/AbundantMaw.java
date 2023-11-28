package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class AbundantMaw extends CardImpl {

    public AbundantMaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Emerge {6}{B}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{6}{B}")));

        // When you cast Abundant Maw, target opponent loses 3 life and you gain 3 life.
        Ability ability = new CastSourceTriggeredAbility(new LoseLifeTargetEffect(3));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AbundantMaw(final AbundantMaw card) {
        super(card);
    }

    @Override
    public AbundantMaw copy() {
        return new AbundantMaw(this);
    }
}
