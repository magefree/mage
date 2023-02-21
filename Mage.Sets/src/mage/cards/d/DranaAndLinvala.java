package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;
import java.util.stream.Collectors;

public class DranaAndLinvala extends CardImpl {
    public DranaAndLinvala(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.VAMPIRE);
        this.addSubType(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        //Activated abilities of creatures your opponents control can't be activated.
        this.addAbility(new SimpleStaticAbility(new DranaAndLinvalaCantActivateEffect()));

        //Drana and Linvala has all activated abilities of all creatures your opponents control. You may spend mana as
        //though it were mana of any color to activate those abilities.
        this.addAbility(new SimpleStaticAbility(new DranaAndLinvalaAllActivatedAbilitiesEffect()));
    }

    private DranaAndLinvala(final DranaAndLinvala card) {
        super(card);
    }

    @Override
    public DranaAndLinvala copy() {
        return new DranaAndLinvala(this);
    }
}

class DranaAndLinvalaCantActivateEffect extends RestrictionEffect {

    public DranaAndLinvalaCantActivateEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "activated abilities of creatures your opponents control can't be activated";
    }

    public DranaAndLinvalaCantActivateEffect(final DranaAndLinvalaCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game)
                && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public DranaAndLinvalaCantActivateEffect copy() {
        return new DranaAndLinvalaCantActivateEffect(this);
    }

}

class DranaAndLinvalaAllActivatedAbilitiesEffect extends ContinuousEffectImpl {

    DranaAndLinvalaAllActivatedAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all creatures your opponents control. You may spend mana " +
                "as though it were mana of any color to activate those abilities.";
        this.dependendToTypes.add(DependencyType.AddingAbility);
    }

    private DranaAndLinvalaAllActivatedAbilitiesEffect(final DranaAndLinvalaAllActivatedAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Set<Ability> abilities = game
                .getBattlefield()
                .getAllActivePermanents(CardType.CREATURE, game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> !card.isControlledBy(source.getControllerId()))
                .map(card -> card.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(ActivatedAbility.class::isInstance)
                .collect(Collectors.toSet());
        for (Ability ability : abilities) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public DranaAndLinvalaAllActivatedAbilitiesEffect copy() {
        return new DranaAndLinvalaAllActivatedAbilitiesEffect(this);
    }
}
