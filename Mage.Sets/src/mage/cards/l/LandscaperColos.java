package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LandscaperColos extends CardImpl {

    private static final FilterCard filter = new FilterCard("card from an opponent's graveyard");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public LandscaperColos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GOAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Landscaper Colos enters the battlefield, put target card from an opponent's graveyard on the bottom of their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(
                false, "put target card from an opponent's graveyard on the bottom of their library"
        ));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Basic landcycling {1}{W}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private LandscaperColos(final LandscaperColos card) {
        super(card);
    }

    @Override
    public LandscaperColos copy() {
        return new LandscaperColos(this);
    }
}
