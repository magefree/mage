package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Grath
 */
public final class GlissaSunslayer extends CardImpl {

    public GlissaSunslayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // First strike,
        this.addAbility(FirstStrikeAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Glissa Sunslayer deals combat damage to a player, choose one —
        // • You draw a card and you lose 1 life.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1).setText("you draw a card"), false, false);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        // • Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        ability.addMode(mode);

        // • Remove up to three counters from target permanent.
        mode = new Mode(new GlissaSunslayerEffect());
        mode.addTarget(new TargetPermanent());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    public GlissaSunslayer(final GlissaSunslayer card) {
        super(card);
    }

    @Override
    public GlissaSunslayer copy() {
        return new GlissaSunslayer(this);
    }
}

class GlissaSunslayerEffect extends OneShotEffect {

    GlissaSunslayerEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Remove up to three counters from target permanent";
    }

    private GlissaSunslayerEffect(final GlissaSunslayerEffect effect) {
        super(effect);
    }

    @Override
    public GlissaSunslayerEffect copy() {
        return new GlissaSunslayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            int toRemove = 3;
            int removed = 0;
            String[] counterNames = permanent.getCounters(game).keySet().toArray(new String[0]);
            for (String counterName : counterNames) {
                if (controller.chooseUse(Outcome.Neutral, "Remove " + counterName + " counters?", source, game)) {
                    if (permanent.getCounters(game).get(counterName).getCount() == 1 || (toRemove - removed == 1)) {
                        permanent.removeCounters(counterName, 1, source, game);
                        removed++;
                    } else {
                        int amount = controller.getAmount(1, Math.min(permanent.getCounters(game).get(counterName).getCount(), toRemove - removed), "How many?", game);
                        if (amount > 0) {
                            removed += amount;
                            permanent.removeCounters(counterName, amount, source, game);
                        }
                    }
                }
                if (removed >= toRemove) {
                    break;
                }
            }
            game.addEffect(new BoostSourceEffect(removed, 0, Duration.EndOfTurn), source);
            return true;
        }
        return true;
    }
}