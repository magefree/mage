package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author noahg
 */
public final class MetamorphicAlteration extends CardImpl {

    public MetamorphicAlteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As Metamorphic Alteration enters the battlefield, choose a creature.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACreature()));

        // Enchanted creature is a copy of the chosen creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MetamorphicAlterationEffect()));
    }

    private MetamorphicAlteration(final MetamorphicAlteration card) {
        super(card);
    }

    @Override
    public MetamorphicAlteration copy() {
        return new MetamorphicAlteration(this);
    }
}

class ChooseACreature extends OneShotEffect {

    public static final String INFO_KEY = "CHOSEN_CREATURE";

    public ChooseACreature() {
        super(Outcome.Copy);
        staticText = "choose a creature";
    }

    public ChooseACreature(final ChooseACreature effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getPermanentEntering(source.getSourceId());
        if (sourceObject == null) {
            sourceObject = game.getObject(source);
        }
        if (controller == null
                || sourceObject == null) {
            return false;
        }
        Target target = new TargetCreaturePermanent();
        target.setNotTarget(true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return true;
        }
        controller.choose(Outcome.Copy, target, source, game);
        Permanent chosenPermanent = game.getPermanent(target.getFirstTarget());
        if (chosenPermanent != null) {
            game.getState().setValue(source.getSourceId().toString() + INFO_KEY, chosenPermanent.copy());
        }
        return true;
    }

    @Override
    public ChooseACreature copy() {
        return new ChooseACreature(this);
    }
}

class MetamorphicAlterationEffect extends ContinuousEffectImpl {

    public MetamorphicAlterationEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.Copy);
        this.staticText = "Enchanted creature is a copy of the chosen creature.";
    }

    public MetamorphicAlterationEffect(MetamorphicAlterationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        Permanent copied = (Permanent) game.getState().getValue(source.getSourceId().toString() + ChooseACreature.INFO_KEY);
        if (enchantment == null
                || copied == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        permanent.setName(copied.getName());
        permanent.getManaCost().clear();
        permanent.getManaCost().addAll(copied.getManaCost());

        CardUtil.copySetAndCardNumber(permanent, copied);

        permanent.removeAllCardTypes(game);
        for (SuperType t : copied.getSuperType(game)) {
            permanent.addSuperType(game, t);
        }
        permanent.removeAllCardTypes(game);
        for (CardType cardType : copied.getCardType(game)) {
            permanent.addCardType(game, cardType);
        }
        permanent.removeAllSubTypes(game);
        permanent.copySubTypesFrom(game, copied);
        permanent.getColor(game).setColor(copied.getColor(game));
        permanent.removeAllAbilities(source.getSourceId(), game);
        for (Ability ability : copied.getAbilities()) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        permanent.getPower().setModifiedBaseValue(copied.getPower().getBaseValue());
        permanent.getToughness().setModifiedBaseValue(copied.getToughness().getBaseValue());
        return true;
    }

    @Override
    public MetamorphicAlterationEffect copy() {
        return new MetamorphicAlterationEffect(this);
    }
}
