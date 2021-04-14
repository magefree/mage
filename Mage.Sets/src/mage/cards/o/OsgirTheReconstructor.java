
package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Arketec
 */
public final class OsgirTheReconstructor extends CardImpl {

    public OsgirTheReconstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}, Sacrifice an artifact: Target creature you control gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact"))));
        this.addAbility(ability);

        // {X},{T}, Exile an artifact with mana value X from your graveyard: Create two tokens that are copies of the exiled card. Activate only as
        Ability copyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new OsgirTheReconstructorCreateArtifactTokensEffect(),
                new ManaCostsImpl("{X}"));
        copyAbility.addCost(new TapSourceCost());

        FilterCard filter = new FilterArtifactCard("an artifact card with mana value X from your graveyard");
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, copyAbility.getManaCostsToPay().getX()));

        copyAbility.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));

        this.addAbility(copyAbility);
    }

    private OsgirTheReconstructor(final OsgirTheReconstructor card) {
        super(card);
    }

    @Override
    public OsgirTheReconstructor copy() {
        return new OsgirTheReconstructor(this);
    }
}

class OsgirTheReconstructorCreateArtifactTokensEffect extends OneShotEffect {

    public OsgirTheReconstructorCreateArtifactTokensEffect() {
        super(Outcome.Benefit);
        this.staticText = "Create two tokens that are copies of the exiled card.";
    }

    public OsgirTheReconstructorCreateArtifactTokensEffect(final OsgirTheReconstructorCreateArtifactTokensEffect effect)  {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Card card = game.getCard(source.getFirstTarget());

        if (player == null || card == null) {
            return false;
        }

        if (!player.moveCards(card, Zone.EXILED, source, game)) {
            return false;
        }

        if (card.isArtifact()) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(player.getId(), null, false, 2);
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            effect.apply(game, source);
            Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game));
            Set<UUID> tokensCreated;
            if (object != null) {
                tokensCreated = (Set<UUID>) object;
            } else {
                tokensCreated = new HashSet<>();
            }
            for (Permanent perm : effect.getAddedPermanent()) {
                if (perm != null) {
                    tokensCreated.add(perm.getId());
                }
            }
            game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
        }

        return  true;
    }

    @Override
    public Effect copy() {
        return new OsgirTheReconstructorCreateArtifactTokensEffect(this);
    }
}
