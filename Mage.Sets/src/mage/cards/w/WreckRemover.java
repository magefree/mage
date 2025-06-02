package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WreckRemover extends CardImpl {

    public WreckRemover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever this creature enters or attacks, exile up to one target card from a graveyard. You gain 1 life.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ExileTargetEffect());
        ability.addEffect(new GainLifeEffect(1));
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private WreckRemover(final WreckRemover card) {
        super(card);
    }

    @Override
    public WreckRemover copy() {
        return new WreckRemover(this);
    }
}
