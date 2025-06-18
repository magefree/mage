package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

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
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD,
                new MayCastTargetCardEffect(true)
                        .setText("you may cast up to one target card of the other type from your graveyard. If a spell cast from your graveyard this way would be put into your graveyard, exile it instead."),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false, SetTargetPointer.SPELL, Zone.HAND
        );
        ability.setTargetAdjuster(GaleWaterdeepProdigyAdjuster.instance);
        this.addAbility(ability);

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

enum GaleWaterdeepProdigyAdjuster implements TargetAdjuster {
    instance;

    private static final FilterCard SORCERY_FILTER = new FilterCard("a sorcery card in your graveyard");
    private static final FilterCard INSTANT_FILTER = new FilterCard("an instant card in your graveyard");

    static {
        SORCERY_FILTER.add(CardType.SORCERY.getPredicate());
        INSTANT_FILTER.add(CardType.INSTANT.getPredicate());
    }
    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID spellId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());

        Spell spell = game.getSpellOrLKIStack(spellId);
        if (spell == null) {
            return;
        }
        FilterCard filter;
        if (spell.isSorcery(game)) {
            filter = INSTANT_FILTER;
        } else {
            filter = SORCERY_FILTER;
        }
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
    }
}
