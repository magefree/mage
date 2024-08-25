package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class AssassinInitiate extends CardImpl {

    public AssassinInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Assassin Initiate gains your choice of flying, deathtouch, or lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source,
                FlyingAbility.getInstance(), DeathtouchAbility.getInstance(), LifelinkAbility.getInstance()),
                new GenericManaCost(1)));
    }

    private AssassinInitiate(final AssassinInitiate card) {
        super(card);
    }

    @Override
    public AssassinInitiate copy() {
        return new AssassinInitiate(this);
    }
}
