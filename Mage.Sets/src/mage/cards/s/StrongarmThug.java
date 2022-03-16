package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class StrongarmThug extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Mercenary card from your graveyard");

    static {
        filter.add(SubType.MERCENARY.getPredicate());
    }

    public StrongarmThug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Strongarm Thug enters the battlefield, you may return target Mercenary card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private StrongarmThug(final StrongarmThug card) {
        super(card);
    }

    @Override
    public StrongarmThug copy() {
        return new StrongarmThug(this);
    }
}
