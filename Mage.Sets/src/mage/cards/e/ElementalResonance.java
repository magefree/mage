
package mage.cards.e;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class ElementalResonance extends CardImpl {

    public ElementalResonance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutManaInPool));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your precombat main phase, add mana equal to enchanted permanent's mana cost.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new ElementalResonanceEffect(), TargetController.YOU, false));
    }

    private ElementalResonance(final ElementalResonance card) {
        super(card);
    }

    @Override
    public ElementalResonance copy() {
        return new ElementalResonance(this);
    }
}

class ElementalResonanceEffect extends OneShotEffect {

    ElementalResonanceEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "add mana equal to enchanted permanent's mana cost.";
    }

    ElementalResonanceEffect(final ElementalResonanceEffect effect) {
        super(effect);
    }

    @Override
    public ElementalResonanceEffect copy() {
        return new ElementalResonanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thisPerm = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (thisPerm == null) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(thisPerm.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<String> manaOptions = new ArrayList<>();
//      TODO: Phyrexian mana gives multiple choices when there should only be one (e.g. Slash Panther is {4} or {4}{R}).
        for (Mana mana : permanent.getManaCost().getOptions()) {
            String manaString = mana.toString();
            if (!manaOptions.contains(manaString)) {
                manaOptions.add(manaString);
            }
        }
        String manaToAdd = "";
        if (manaOptions.size() > 1) {
//          TODO: Make the choices look nicer, right now the brace notation is hard to visually parse, especially with Reaper King
            Choice choice = new ChoiceImpl();
            choice.setMessage("Choose a mana combination");
            choice.getChoices().addAll(manaOptions);
            if (!controller.choose(Outcome.PutManaInPool, choice, game)) {
                return false;
            }
            manaToAdd = choice.getChoice();
        } else if (manaOptions.size() == 1) {
            manaToAdd = manaOptions.get(0);
        }
        if (!manaToAdd.equals("")) {
            controller.getManaPool().addMana(getManaFromString(manaToAdd), game, source);
        }
        return true;
    }

    private static Mana getManaFromString(String manaString) {
        Mana out = new Mana(0, 0, 0, 0, 0, 0, 0, 0);
        Integer generic = 0;
        for (String str : Arrays.asList(manaString.replaceAll("[^-?0-9]+", " ").trim().split(" "))) {
            if (!str.equals("")) {
                generic += Integer.parseInt(str);
            }
        }
        out.setColorless(generic);
        for (char c : manaString.toCharArray()) {
            switch (c) {
                case 'W':
                    out.increaseWhite();
                    break;
                case 'U':
                    out.increaseBlue();
                    break;
                case 'B':
                    out.increaseBlack();
                    break;
                case 'R':
                    out.increaseRed();
                    break;
                case 'G':
                    out.increaseGreen();
                    break;
                case 'C':
                    out.increaseColorless();
                    break;
            }
        }
        return out;
    }
}
