package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReturnedPastcaller extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirit, instant, or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(
                SubType.SPIRIT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public ReturnedPastcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R/W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Returned Pastcaller enters the battlefield, return target Spirit, instant, or sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ReturnedPastcaller(final ReturnedPastcaller card) {
        super(card);
    }

    @Override
    public ReturnedPastcaller copy() {
        return new ReturnedPastcaller(this);
    }
}
