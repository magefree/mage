
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class NantukoVigilante extends CardImpl {

    public NantukoVigilante(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Morph {1}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{G}")));
        // When Nantuko Vigilante is turned face up, destroy target artifact or enchantment.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private NantukoVigilante(final NantukoVigilante card) {
        super(card);
    }

    @Override
    public NantukoVigilante copy() {
        return new NantukoVigilante(this);
    }
}
