package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MyrWelder extends CardImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("artifact card from a graveyard");

    public MyrWelder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Imprint - {tap}: Exile target artifact card from a graveyard
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MyrWelderEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.IMPRINT));

        // Myr Welder has all activated abilities of all cards exiled with it
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MyrWelderContinuousEffect()));

    }

    private MyrWelder(final MyrWelder card) {
        super(card);
    }

    @Override
    public MyrWelder copy() {
        return new MyrWelder(this);
    }

}

class MyrWelderEffect extends OneShotEffect {

    MyrWelderEffect() {
        super(Outcome.Exile);
        staticText = "Exile target artifact card from a graveyard";
    }

    private MyrWelderEffect(final MyrWelderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (card != null && permanent != null) {
            card.moveToExile(getId(), "Myr Welder (Imprint)", source, game);
            permanent.imprint(card.getId(), game);
            return true;
        }
        return false;
    }

    @Override
    public MyrWelderEffect copy() {
        return new MyrWelderEffect(this);
    }

}

class MyrWelderContinuousEffect extends ContinuousEffectImpl {

    public MyrWelderContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all cards exiled with it";
    }

    private MyrWelderContinuousEffect(final MyrWelderContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            for (UUID imprintedId : perm.getImprinted()) {
                Card card = game.getCard(imprintedId);
                if (card != null) {
                    for (Ability ability : card.getAbilities(game)) {
                        if (ability instanceof ActivatedAbility) {
                            perm.addAbility(ability, source.getId(), game, true);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public MyrWelderContinuousEffect copy() {
        return new MyrWelderContinuousEffect(this);
    }

}
