package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class AngelOfGlorysRise extends CardImpl {

    private static final FilterPermanent filterZombie = new FilterPermanent(SubType.ZOMBIE , "Zombies");
    private static final FilterCreatureCard filterHuman = new FilterCreatureCard("Human creature cards");
    static {
        filterHuman.add(SubType.HUMAN.getPredicate());
    }

    public AngelOfGlorysRise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // When Angel of Glory's Rise enters the battlefield, exile all Zombies, then return all Human creature cards from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileAllEffect(filterZombie));
        ability.addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filterHuman).concatBy(", then"));
        this.addAbility(ability);
    }

    private AngelOfGlorysRise(final AngelOfGlorysRise card) {
        super(card);
    }

    @Override
    public AngelOfGlorysRise copy() {
        return new AngelOfGlorysRise(this);
    }
}
