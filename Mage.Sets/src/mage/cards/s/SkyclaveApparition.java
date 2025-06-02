package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateXXTokenExiledEffectManaValueEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.CustomIllusionToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclaveApparition extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent(
            "nonland, nontoken permanent you don't control with mana value 4 or less"
    );

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public SkyclaveApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Skyclave Apparition enters the battlefield, exile up to one target nonland, nontoken permanent you don't control with converted mana cost 4 or less.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // When Skyclave Apparition leaves the battlefield, the exiled card's owner creates an X/X blue Illusion creature token, where X is the converted mana cost of the exiled card.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new CreateXXTokenExiledEffectManaValueEffect(CustomIllusionToken::new, "blue Illusion")
        ));
    }

    private SkyclaveApparition(final SkyclaveApparition card) {
        super(card);
    }

    @Override
    public SkyclaveApparition copy() {
        return new SkyclaveApparition(this);
    }
}
