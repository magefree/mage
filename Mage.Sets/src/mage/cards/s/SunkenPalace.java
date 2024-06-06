package mage.cards.s;

import java.util.List;
import java.util.Stack;
import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.CopySpellForEachItCouldTargetEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author grimreap124
 */
public final class SunkenPalace extends CardImpl {

    public SunkenPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.LAND }, "");

        this.subtype.add(SubType.CAVE);

        // Sunken Palace enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
        // {1}{U}, {T}, Exile seven cards from your graveyard: Add {U}.
        Ability ability = new BlueManaAbility();

        ability.addCost(new ManaCostsImpl<>("{1}{U}"));

        // ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(7)));
        // When you spend this mana to cast a spell or activate an ability, copy that spell or ability. You may choose new targets for the copy.

        Effect effect = new CopyTargetStackObjectEffect(true);
        effect.setText("copy that spell or ability");
        ManaSpentDelayedTriggeredAbility manaSpentDelayedTriggeredAbility = new ManaSpentDelayedTriggeredAbility(new CopyTargetStackObjectEffect(),
                StaticFilters.FILTER_SPELL_OR_ABILITY, true);

        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(manaSpentDelayedTriggeredAbility));
        this.addAbility(ability);

    }

    private SunkenPalace(final SunkenPalace card) {
        super(card);
    }

    @Override
    public SunkenPalace copy() {
        return new SunkenPalace(this);
    }
}

class SunkenPalaceEffect extends OneShotEffect {

    SunkenPalaceEffect() {
        super(Outcome.Benefit);
        staticText = "copy that spell or ability. You may choose new targets for the copy";
    }

    private SunkenPalaceEffect(final SunkenPalaceEffect effect) {
        super(effect);
    }

    @Override
    public SunkenPalaceEffect copy() {
        return new SunkenPalaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // This is always null I need to get the sourceId of the stackObject that
        UUID  sourceSouceId = source.getSourceId();
        game.informPlayers("sourceSouceId: " + sourceSouceId);
        game.informPlayers("sourceId: " + source.getId());

        List<UUID> target = getTargetPointer().getTargets(game, source);
        if (target == null) {
            game.informPlayers("manaspend ability null ");
            return false;
        }
        game.informPlayers("manaspend ability null "+ target.toString());
        StackObject stackObject = game.getStack().getStackObject(target.get(0));

        if (stackObject == null) {
            game.informPlayers("Stackobject null");
            return false;
        }
        stackObject.createCopyOnStack(game, source, source.getControllerId(), true);
        return true;
    }
}