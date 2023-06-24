
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class DreamCoat extends CardImpl {

    public DreamCoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {0}: Enchanted creature becomes the color or colors of your choice. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BecomesColorOrColorsEnchantedEffect(), new GenericManaCost(0), 1));
    }

    private DreamCoat(final DreamCoat card) {
        super(card);
    }

    @Override
    public DreamCoat copy() {
        return new DreamCoat(this);
    }
}

class BecomesColorOrColorsEnchantedEffect extends OneShotEffect {

    public BecomesColorOrColorsEnchantedEffect() {
        super(Outcome.Neutral);
        this.staticText = "Enchanted creature becomes the color or colors of your choice";
    }

    public BecomesColorOrColorsEnchantedEffect(final BecomesColorOrColorsEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (enchantment == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        StringBuilder sb = new StringBuilder();

        if (controller == null || permanent == null) {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            if (i > 0) {
                if (!controller.chooseUse(Outcome.Neutral, "Choose another color?", source, game)) {
                    break;
                }
            }
            ChoiceColor choiceColor = new ChoiceColor();
            if (!controller.choose(Outcome.Benefit, choiceColor, game)) {
                return false;
            }
            if (!game.isSimulation()) {
                game.informPlayers(permanent.getName() + ": " + controller.getLogName() + " has chosen " + choiceColor.getChoice());
            }
            if (choiceColor.getColor().isBlack()) {
                sb.append('B');
            } else if (choiceColor.getColor().isBlue()) {
                sb.append('U');
            } else if (choiceColor.getColor().isRed()) {
                sb.append('R');
            } else if (choiceColor.getColor().isGreen()) {
                sb.append('G');
            } else if (choiceColor.getColor().isWhite()) {
                sb.append('W');
            }
        }
        String colors = new String(sb);
        ObjectColor chosenColors = new ObjectColor(colors);
        ContinuousEffect effect = new BecomesColorTargetEffect(chosenColors, Duration.Custom);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);

        return true;
    }

    @Override
    public BecomesColorOrColorsEnchantedEffect copy() {
        return new BecomesColorOrColorsEnchantedEffect(this);
    }
}
