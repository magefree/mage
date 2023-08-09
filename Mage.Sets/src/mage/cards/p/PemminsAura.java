
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PemminsAura extends CardImpl {

    public PemminsAura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {U}: Untap enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapAttachedEffect(), new ManaCostsImpl<>("{U}")));

        // {U}: Enchanted creature gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(),
                AttachmentType.AURA, Duration.EndOfTurn), new ManaCostsImpl<>("{U}")));

        // {U}: Enchanted creature gains shroud until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ShroudAbility.getInstance(),
                AttachmentType.AURA, Duration.EndOfTurn), new ManaCostsImpl<>("{U}")));

        // {1}: Enchanted creature gets +1/-1 or -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PemminsAuraBoostEnchantedEffect(), new ManaCostsImpl<>("{1}")));
    }

    private PemminsAura(final PemminsAura card) {
        super(card);
    }

    @Override
    public PemminsAura copy() {
        return new PemminsAura(this);
    }
}

class PemminsAuraBoostEnchantedEffect extends OneShotEffect {

    private static String CHOICE_1 = "+1/-1";
    private static String CHOICE_2 = "-1/+1";

    public PemminsAuraBoostEnchantedEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Enchanted creature gets +1/-1 or -1/+1 until end of turn";
    }

    public PemminsAuraBoostEnchantedEffect(final PemminsAuraBoostEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public PemminsAuraBoostEnchantedEffect copy() {
        return new PemminsAuraBoostEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (controller == null || enchantment == null) {
            return false;
        }

        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        if (creature == null) {
            return false;
        }

        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Select how to boost");
        choice.getChoices().add(CHOICE_1);
        choice.getChoices().add(CHOICE_2);
        if (controller.choose(outcome, choice, game)) {
            if (choice.getChoice().equals(CHOICE_1)) {
                game.addEffect(new BoostEnchantedEffect(+1, -1, Duration.EndOfTurn), source);
            } else {
                game.addEffect(new BoostEnchantedEffect(-1, +1, Duration.EndOfTurn), source);
            }
            return true;
        }

        return false;
    }
}
