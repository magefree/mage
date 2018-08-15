package mage.cards.m;

import java.util.UUID;
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

/**
 *
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
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // As Metamorphic Alteration enters the battlefield, choose a creature.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACreature()));

        // Enchanted creature is a copy of the chosen creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MetamorphicAlterationEffect()));
    }

    public MetamorphicAlteration(final MetamorphicAlteration card) {
        super(card);
    }

    @Override
    public MetamorphicAlteration copy() {
        return new MetamorphicAlteration(this);
    }
}

class ChooseACreature extends OneShotEffect {

    public static String INFO_KEY = "CHOSEN_CREATURE";

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
            sourceObject = game.getObject(source.getSourceId());
        }
        if (controller != null && sourceObject != null) {
            Target target = new TargetCreaturePermanent();
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
                controller.choose(Outcome.Copy, target, source.getSourceId(), game);
                Permanent chosenPermanent = game.getPermanent(target.getFirstTarget());
                if (chosenPermanent != null) {
                    game.getState().setValue(source.getSourceId().toString() + INFO_KEY, chosenPermanent.copy());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ChooseACreature copy() {
        return new ChooseACreature(this);
    }
}

class MetamorphicAlterationEffect extends ContinuousEffectImpl {

    public MetamorphicAlterationEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.NA, Outcome.Copy);
        this.staticText = "Enchanted creature is a copy of the chosen creature.";
    }

    public MetamorphicAlterationEffect(MetamorphicAlterationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        //TODO this is stupid and there's got to be a better way, but it works and people probably want to practice full M19 before Prerelease.
        Permanent enchantment = game.getPermanent(source.getSourceId());
        Permanent copied = (Permanent) game.getState().getValue(source.getSourceId().toString() + ChooseACreature.INFO_KEY);
        if (enchantment != null && copied != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null && layer == Layer.CopyEffects_1) {
                permanent.setName(copied.getName());
                permanent.getManaCost().clear();
                permanent.getManaCost().addAll(copied.getManaCost());
                permanent.setExpansionSetCode(copied.getExpansionSetCode());
                permanent.getSuperType().clear();
                for (SuperType t : copied.getSuperType()) {
                    permanent.addSuperType(t);
                }
                permanent.getCardType().clear();
                for (CardType t : copied.getCardType()) {
                    permanent.addCardType(t);
                }
                permanent.getSubtype(game).retainAll(SubType.getLandTypes(false));
                for (SubType t : copied.getSubtype(game)) {
                    permanent.getSubtype(game).add(t);
                }
                permanent.getColor(game).setColor(copied.getColor(game));
                permanent.removeAllAbilities(source.getSourceId(), game);
                for (Ability ability : copied.getAbilities()) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                }
                permanent.getPower().setValue(copied.getPower().getValue());
                permanent.getToughness().setValue(copied.getToughness().getValue());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public MetamorphicAlterationEffect copy() {
        return new MetamorphicAlterationEffect(this);
    }
}
