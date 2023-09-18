package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class CurseOfThirst extends CardImpl {

    public CurseOfThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of enchanted player's upkeep, Curse of Thirst deals damage to that player equal to the number of Curses attached to them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageTargetEffect(CursesAttachedCount.instance)
                        .setText("{this} deals damage to that player equal to the number of Curses attached to them"),
                TargetController.ENCHANTED, false
        ));
    }

    private CurseOfThirst(final CurseOfThirst card) {
        super(card);
    }

    @Override
    public CurseOfThirst copy() {
        return new CurseOfThirst(this);
    }
}

enum CursesAttachedCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(effect.getTargetPointer().getFirst(game, sourceAbility));
        if (player == null) {
            return 0;
        }
        return player
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(p -> p.hasSubtype(SubType.CURSE, game))
                .mapToInt(x -> x ? 1 : 0)
                .sum();
    }

    @Override
    public CursesAttachedCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "number of Curses attached to them";
    }
}
