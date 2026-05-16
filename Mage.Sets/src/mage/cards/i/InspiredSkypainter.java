package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author muz
 */
public final class InspiredSkypainter extends PrepareCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public InspiredSkypainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}", "Maestro's Gift", CardType.SORCERY, "{3}{U}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters and whenever one or more creature tokens you control deal combat damage to a player, this creature becomes prepared.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new BecomePreparedSourceEffect(),
                false,
                "When this creature enters and whenever one or more creature tokens you control deal combat damage to a player, ",
                new EntersBattlefieldTriggeredAbility(null),
                new OneOrMoreCombatDamagePlayerTriggeredAbility(null, filter)
        ));

        // Maestro's Gift
        // Sorcery {3}{U}{R}
        // Create a token that's a copy of target creature you control. That token gains haste until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new InspiredSkypainterEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private InspiredSkypainter(final InspiredSkypainter card) {
        super(card);
    }

    @Override
    public InspiredSkypainter copy() {
        return new InspiredSkypainter(this);
    }
}

class InspiredSkypainterEffect extends CreateTokenCopyTargetEffect {

    InspiredSkypainterEffect() {
        super(null, null, false);
        staticText = "create a token that's a copy of target creature you control. That token gains haste until end of turn";
    }

    private InspiredSkypainterEffect(final InspiredSkypainterEffect effect) {
        super(effect);
    }

    @Override
    public InspiredSkypainterEffect copy() {
        return new InspiredSkypainterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!super.apply(game, source)) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setTargetPointer(new FixedTargets(getAddedPermanents(), game)), source);
        return true;
    }
}
