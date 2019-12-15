
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOtherCreatureSharingCreatureSubtype;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author t-schroeder
 */
public final class CrownOfFury extends CardImpl {

    public CrownOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);
        
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+0 and has first strike.
        Effect effect = new BoostEnchantedEffect(1, 0, Duration.WhileOnBattlefield);
        effect.setText("enchanted creature gets +1/+0");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has first strike");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Sacrifice Crown of Fury: Enchanted creature and other creatures that share a creature type with it get +1/+0 and gain first strike until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrownOfFuryEffect(), new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public CrownOfFury(final CrownOfFury card) {
        super(card);
    }

    @Override
    public CrownOfFury copy() {
        return new CrownOfFury(this);
    }
}

class CrownOfFuryEffect extends OneShotEffect {

    public CrownOfFuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Enchanted creature and other creatures that share a creature type with it get +1/+0 and gain first strike until end of turn.";
    }

    public CrownOfFuryEffect(final CrownOfFuryEffect effect) {
        super(effect);
    }

    @Override
    public CrownOfFuryEffect copy() {
        return new CrownOfFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // Enchanted creature ...
        ContinuousEffect effect = new BoostEnchantedEffect(1, 0, Duration.EndOfTurn);
        game.addEffect(effect, source);
        effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn);
        game.addEffect(effect, source);

        // ... and other creatures that share a creature type with it ...
        Permanent enchantedCreature = game.getPermanent(source.getSourcePermanentOrLKI(game).getAttachedTo());
        FilterCreaturePermanent filter = new FilterOtherCreatureSharingCreatureSubtype(enchantedCreature, game);
        game.addEffect(new BoostAllEffect(1, 0, Duration.EndOfTurn, filter, false), source);
        game.addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter), source);

        // ... get +1/+0 and gain first strike until end of turn.
        return true;
    }
}
