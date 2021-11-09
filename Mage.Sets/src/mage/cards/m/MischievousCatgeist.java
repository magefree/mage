package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MischievousCatgeist extends CardImpl {

    public MischievousCatgeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.c.CatlikeCuriosity.class;

        // Whenever Mischievous Catgeist deals combat damage to a player, draw card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));

        // Disturb {2}{U}
        this.addAbility(new DisturbAbility(this, "{2}{U}"));
    }

    private MischievousCatgeist(final MischievousCatgeist card) {
        super(card);
    }

    @Override
    public MischievousCatgeist copy() {
        return new MischievousCatgeist(this);
    }
}
