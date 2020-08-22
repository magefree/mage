package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnEvangel extends CardImpl {

    public DawnEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature dies, if an Aura you control was attached to it, return target creature card with converted mana cost 2 or less from your graveyard to your hand.
        this.addAbility(new DawnEvangelAbility());
    }

    private DawnEvangel(final DawnEvangel card) {
        super(card);
    }

    @Override
    public DawnEvangel copy() {
        return new DawnEvangel(this);
    }
}

class DawnEvangelAbility extends DiesCreatureTriggeredAbility {

    private static final FilterCard cardFilter
            = new FilterCreatureCard("creature card with converted mana cost 2 or less from your graveyard");

    static {
        cardFilter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
    }

    DawnEvangelAbility() {
        super(new ReturnFromGraveyardToHandTargetEffect(), false);
        this.addTarget(new TargetCardInYourGraveyard(cardFilter));
    }

    private DawnEvangelAbility(DawnEvangelAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent
                .getTarget()
                .getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.hasSubtype(SubType.AURA, game))
                .map(Permanent::getControllerId)
                .anyMatch(getControllerId()::equals);
    }

    @Override
    public String getRule() {
        return "Whenever a creature dies, if an Aura you controlled was attached to it, " +
                "return target creature card with converted mana cost 2 or less from your graveyard to your hand.";
    }

    @Override
    public DawnEvangelAbility copy() {
        return new DawnEvangelAbility(this);
    }
}