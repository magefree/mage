package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScavengingHarpy extends CardImpl {

    private static final FilterCard filter = new FilterCard("card from an opponent's graveyard");

    public ScavengingHarpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HARPY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Scavenging Harpy enters the battlefield, exile target card from an opponent's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    private ScavengingHarpy(final ScavengingHarpy card) {
        super(card);
    }

    @Override
    public ScavengingHarpy copy() {
        return new ScavengingHarpy(this);
    }
}
