package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class GryffsBoon extends CardImpl {

    public GryffsBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+0 and has flying.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 0, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has flying");
        ability.addEffect(effect);
        this.addAbility(ability);

        // {3}{W}: Return Gryff's Boon from your graveyard to the battlefield attached to target creature. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD, new GryffsBoonEffect(), new ManaCostsImpl<>("{3}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GryffsBoon(final GryffsBoon card) {
        super(card);
    }

    @Override
    public GryffsBoon copy() {
        return new GryffsBoon(this);
    }
}

class GryffsBoonEffect extends OneShotEffect {

    public GryffsBoonEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return {this} from your graveyard to the battlefield attached to target creature";
    }

    public GryffsBoonEffect(final GryffsBoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card aura = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (aura != null && controller != null
                && game.getState().getZone(aura.getId()) == Zone.GRAVEYARD) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (!targetPermanent.cantBeAttachedBy(aura, source, game, false)) {
                game.getState().setValue("attachTo:" + aura.getId(), targetPermanent);
                controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
                return targetPermanent.addAttachment(aura.getId(), source, game);
            }
        }
        return false;
    }

    @Override
    public GryffsBoonEffect copy() {
        return new GryffsBoonEffect(this);
    }
}
