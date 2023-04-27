package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatteredEgo extends CardImpl {

    public ShatteredEgo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets -3/-0.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(-3, 0)));

        // {3}{U}{U}: Put enchanted creature into its owner's library third from the top.
        this.addAbility(new SimpleActivatedAbility(new ShatteredEgoEffect(), new ManaCostsImpl<>("{3}{U}{U}")));
    }

    private ShatteredEgo(final ShatteredEgo card) {
        super(card);
    }

    @Override
    public ShatteredEgo copy() {
        return new ShatteredEgo(this);
    }
}

class ShatteredEgoEffect extends OneShotEffect {

    ShatteredEgoEffect() {
        super(Outcome.Benefit);
        staticText = "put enchanted creature into its owner's library third from the top";
    }

    private ShatteredEgoEffect(final ShatteredEgoEffect effect) {
        super(effect);
    }

    @Override
    public ShatteredEgoEffect copy() {
        return new ShatteredEgoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent aura = source.getSourcePermanentOrLKI(game);
        if (player == null || aura == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(aura.getAttachedTo());
        return permanent != null && player.putCardOnTopXOfLibrary(
                permanent, game, source, 3, true
        );
    }
}
