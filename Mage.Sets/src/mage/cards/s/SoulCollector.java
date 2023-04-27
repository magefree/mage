package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author markedagain
 */
public final class SoulCollector extends CardImpl {

    public SoulCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature dealt damage by Soul Collector this turn dies, return that card to the battlefield under your control.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new ReturnToBattlefieldUnderYourControlTargetEffect()));

        // Morph {B}{B}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{B}{B}{B}")));
    }

    private SoulCollector(final SoulCollector card) {
        super(card);
    }

    @Override
    public SoulCollector copy() {
        return new SoulCollector(this);
    }
}
