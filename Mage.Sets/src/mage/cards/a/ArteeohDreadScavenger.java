package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ArteeohDreadScavenger extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("other target artifacts");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ArteeohDreadScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Arteeoh deals combat damage to a player, you may exchange control of two other target artifacts. When you do, create a token that's a copy of target artifact you don't control, except it's a 1/1 green Squirrel creature token in addition to its other colors and types.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ArteeohDreadScavengerEffect(), true);
        ability.addTarget(new TargetPermanent(2, filter));
        this.addAbility(ability);
    }

    private ArteeohDreadScavenger(final ArteeohDreadScavenger card) {
        super(card);
    }

    @Override
    public ArteeohDreadScavenger copy() {
        return new ArteeohDreadScavenger(this);
    }
}

class ArteeohDreadScavengerEffect extends OneShotEffect {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    ArteeohDreadScavengerEffect() {
        super(Outcome.Benefit);
        staticText = "exchange control of two other target artifacts. When you do, create a token that's a copy of " +
                "target artifact you don't control, except it's a 1/1 green Squirrel creature token in addition " +
                "to its other colors and types";
    }

    private ArteeohDreadScavengerEffect(final ArteeohDreadScavengerEffect effect) {
        super(effect);
    }

    @Override
    public ArteeohDreadScavengerEffect copy() {
        return new ArteeohDreadScavengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.size() != 2) {
            return false;
        }
        for (UUID targetID : getTargetPointer().getTargets(game, source)) {
            if (game.getPermanent(targetID) == null) return false;
        }
        game.addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "exchange control of two other target artifacts"), source);

        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, false, 1, false,
                false, null, 1, 1, false
        );
        effect.setExtraColor(ObjectColor.GREEN);
        effect.withAdditionalSubType(SubType.SQUIRREL);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(effect, false,
                "create a token that's a copy of target artifact you don't control, except it's " +
                "a 1/1 green Squirrel creature token in addition to its other colors and types"
        );
        ability.addTarget(new TargetPermanent(1, filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
