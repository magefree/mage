package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Nucklavee extends CardImpl {
    private static final FilterCard filterRed = new FilterCard("red sorcery card from your graveyard");
    private static final FilterCard filterBlue = new FilterCard("blue instant card from your graveyard");

    static {
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterRed.add(CardType.SORCERY.getPredicate());
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlue.add(CardType.INSTANT.getPredicate());
    }

    public Nucklavee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U/R}{U/R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Nucklavee enters the battlefield, you may return target red sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filterRed));
        this.addAbility(ability);
        // When Nucklavee enters the battlefield, you may return target blue instant card from your graveyard to your hand.
        ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filterBlue));
        this.addAbility(ability);
    }

    private Nucklavee(final Nucklavee card) {
        super(card);
    }

    @Override
    public Nucklavee copy() {
        return new Nucklavee(this);
    }
}
