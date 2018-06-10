
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SealockMonster extends CardImpl {

    public SealockMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.OCTOPUS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sealock Monster can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND,"an Island"))));
        // {5}{U}{U}: Monstrosity 3.</i>
        this.addAbility(new MonstrosityAbility("{5}{U}{U}",3));
        // When Sealock Monster becomes monstrous, target land becomes an island in addition to its other types.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new SealockMonsterBecomesIslandTargetEffect());
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public SealockMonster(final SealockMonster card) {
        super(card);
    }

    @Override
    public SealockMonster copy() {
        return new SealockMonster(this);
    }
}

class SealockMonsterBecomesIslandTargetEffect extends ContinuousEffectImpl {

    private static Ability islandAbility = new BlueManaAbility();

    public SealockMonsterBecomesIslandTargetEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.staticText = "target land becomes an island in addition to its other types";

    }

    public SealockMonsterBecomesIslandTargetEffect(final SealockMonsterBecomesIslandTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SealockMonsterBecomesIslandTargetEffect copy() {
        return new SealockMonsterBecomesIslandTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID targetPermanent : targetPointer.getTargets(game, source)) {
            Permanent land = game.getPermanent(targetPermanent);
            if (land != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        if (!land.getAbilities().containsRule(islandAbility)) {
                            land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case TypeChangingEffects_4:
                        if (!land.hasSubtype(SubType.ISLAND, game)) {
                            land.getSubtype(game).add(SubType.ISLAND);
                        }
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

}
