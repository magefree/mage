package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamwiseTheStouthearted extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card in your graveyard that was put there from the battlefield this turn");

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public SamwiseTheStouthearted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Samwise the Stouthearted enters the battlefield, choose up to one target permanent card in your graveyard that was put there from the battlefield this turn. Return it to your hand. Then the Ring tempts you.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()
                .setText("choose up to one target permanent card in your graveyard that was put there from the battlefield this turn. Return it to your hand"));
        ability.addEffect(new TheRingTemptsYouEffect().concatBy("Then"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability, new CardsPutIntoGraveyardWatcher());
    }

    private SamwiseTheStouthearted(final SamwiseTheStouthearted card) {
        super(card);
    }

    @Override
    public SamwiseTheStouthearted copy() {
        return new SamwiseTheStouthearted(this);
    }
}
