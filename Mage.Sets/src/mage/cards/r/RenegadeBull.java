package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RenegadeBull extends CardImpl {

    public RenegadeBull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.OX);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, this creature gets +X/+0 until end of turn, where X is that spell's mana value.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(RenegadeBullSpellManaValue.instance, StaticValue.get(0), Duration.EndOfTurn)
                        .setText("this creature gets +X/+0 until end of turn, where X is that spell's mana value"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // Whenever this creature attacks, exile up to one target instant or sorcery card from your graveyard and copy it. You may cast the copy without paying its mana cost.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetCardCopyAndCastEffect(true)
                .setText("exile up to one target instant or sorcery card from your graveyard and copy it. " +
                        "You may cast the copy without paying its mana cost"));
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);
    }

    private RenegadeBull(final RenegadeBull card) {
        super(card);
    }

    @Override
    public RenegadeBull copy() {
        return new RenegadeBull(this);
    }
}

enum RenegadeBullSpellManaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .map(Spell::getManaValue)
                .orElse(0);
    }

    @Override
    public RenegadeBullSpellManaValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }
}
