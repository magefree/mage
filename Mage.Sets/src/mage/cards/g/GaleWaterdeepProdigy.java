package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author Rjayz
 */
public final class GaleWaterdeepProdigy extends CardImpl {

    public GaleWaterdeepProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell from your hand,
        // you may cast up to one of the other type from your graveyard.
        // If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.
        this.addAbility(new GaleWaterdeepProdigyTriggeredAbility());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private GaleWaterdeepProdigy(final GaleWaterdeepProdigy card) {
        super(card);
    }

    @Override
    public GaleWaterdeepProdigy copy() {
        return new GaleWaterdeepProdigy(this);
    }
}

class GaleWaterdeepProdigyTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterCard SORCERY_FILTER = new FilterCard("a sorcery card in your graveyard");
    private static final FilterCard INSTANT_FILTER = new FilterCard("an instant card in your graveyard");

    static {
        SORCERY_FILTER.add(CardType.SORCERY.getPredicate());
        INSTANT_FILTER.add(CardType.INSTANT.getPredicate());
    }

    public GaleWaterdeepProdigyTriggeredAbility() {
        super(
                new MayCastTargetCardEffect(true)
                        .setText("you may cast up to one target card of the other type from your graveyard. "
                                + "If a spell cast from your graveyard this way would be put into your graveyard, exile it instead."),
                new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand"),
                false
        );
        addWatcher(new CastFromHandWatcher());
    }

    private GaleWaterdeepProdigyTriggeredAbility(final GaleWaterdeepProdigyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }

        CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
        if (watcher == null || !watcher.spellWasCastFromHand(event.getSourceId())) {
            return false;
        }

        Spell spell = game.getState().getStack().getSpell(event.getSourceId());
        if (spell == null) {
            return false;
        }

        FilterCard filterCard;
        if (spell.isSorcery(game)) {
            filterCard = INSTANT_FILTER;
        } else {
            filterCard = SORCERY_FILTER;
        }
        this.getTargets().clear();
        this.getTargets().add(new TargetCardInYourGraveyard(filterCard));
        return true;
    }

    @Override
    public GaleWaterdeepProdigyTriggeredAbility copy() {
        return new GaleWaterdeepProdigyTriggeredAbility(this);
    }
}