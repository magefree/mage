
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class KarnSilverGolem extends CardImpl {

    private static final FilterArtifactPermanent filterNonCreature = new FilterArtifactPermanent("noncreature artifact");

    static {
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }
    
    public KarnSilverGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Karn, Silver Golem blocks or becomes blocked, it gets -4/+4 until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(-4, +4, Duration.EndOfTurn), false));
        
        // {1}: Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KarnSilverGolemEffect(), new ManaCostsImpl<>("{1}"));
        ability.addTarget(new TargetPermanent(filterNonCreature));
        this.addAbility(ability);        
    }

    private KarnSilverGolem(final KarnSilverGolem card) {
        super(card);
    }

    @Override
    public KarnSilverGolem copy() {
        return new KarnSilverGolem(this);
    }
}

class KarnSilverGolemEffect extends ContinuousEffectImpl {

    public KarnSilverGolemEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Target noncreature artifact becomes an artifact creature with power and toughness each equal to its mana value until end of turn";
    }

    public KarnSilverGolemEffect(final KarnSilverGolemEffect effect) {
        super(effect);
    }

    @Override
    public KarnSilverGolemEffect copy() {
        return new KarnSilverGolemEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent artifact = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (artifact == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    if (!artifact.isCreature(game)) {
                        artifact.addCardType(game, CardType.CREATURE);
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    int cmc = artifact.getManaValue();
                    artifact.getPower().setValue(cmc);
                    artifact.getToughness().setValue(cmc);
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }


    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
