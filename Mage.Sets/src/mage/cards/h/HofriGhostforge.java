package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HofriGhostforge extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.SPIRIT, "Spirits");
    private static final FilterPermanent filter2
            = new FilterPermanent(SubType.SPIRIT, "");
    private static final FilterPermanent filter3
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter3.add(AnotherPredicate.instance);
        filter3.add(TokenPredicate.FALSE);
    }

    public HofriGhostforge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Spirits you control get +1/+1 and have trample and haste.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new CompoundAbility(
                        TrampleAbility.getInstance(), HasteAbility.getInstance()
                ), Duration.WhileOnBattlefield, filter2
        ).setText("and have trample and haste"));
        this.addAbility(ability);

        // Whenever another nontoken creature you control dies, exile it. If you do, create a token that's a copy of that creature, except it's a Spirit in addition to its other types and it has "When this creature leaves the battlefield, return the exiled card to your graveyard."
        this.addAbility(new DiesCreatureTriggeredAbility(
                new HofriGhostforgeEffect(), false, filter3, true
        ));
    }

    private HofriGhostforge(final HofriGhostforge card) {
        super(card);
    }

    @Override
    public HofriGhostforge copy() {
        return new HofriGhostforge(this);
    }
}

class HofriGhostforgeEffect extends OneShotEffect {

    HofriGhostforgeEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. If you do, create a token that's a copy of that creature, " +
                "except it's a Spirit in addition to its other types and it has " +
                "\"When this creature leaves the battlefield, return the exiled card to its owner's graveyard.\"";
    }

    private HofriGhostforgeEffect(final HofriGhostforgeEffect effect) {
        super(effect);
    }

    @Override
    public HofriGhostforgeEffect copy() {
        return new HofriGhostforgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId());
        effect.setTargetPointer(new FixedTarget(card, game));
        effect.setAdditionalSubType(SubType.SPIRIT);
        effect.addAdditionalAbilities(new ZoneChangeTriggeredAbility(
                Zone.ALL, Zone.BATTLEFIELD, null, new HofriGhostforgeReturnEffect(card, game),
                "When this creature leaves the battlefield, ", false
        ));
        effect.apply(game, source);
        return true;
    }
}

class HofriGhostforgeReturnEffect extends OneShotEffect {

    private final MageObjectReference mor;

    HofriGhostforgeReturnEffect(Card card, Game game) {
        super(Outcome.Benefit);
        this.mor = new MageObjectReference(card, game);
        staticText = "return the exiled card to its owner's graveyard";
    }

    private HofriGhostforgeReturnEffect(final HofriGhostforgeReturnEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public HofriGhostforgeReturnEffect copy() {
        return new HofriGhostforgeReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = mor.getCard(game);
        return player != null && card != null && player.moveCards(card, Zone.GRAVEYARD, source, game);
    }
}
