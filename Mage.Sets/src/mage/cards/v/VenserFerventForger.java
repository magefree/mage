package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class VenserFerventForger extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("instant or sorcery spell an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VenserFerventForger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Venser enters, choose one --
        // * Copy target instant or sorcery spell an opponent controls twice. You may choose new targets for the copies.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new CopyTargetStackObjectEffect(false, false, true, 2, null)
        );
        ability.addTarget(new TargetSpell(filter));

        // * Create two tokens that are copies of target permanent an opponent controls. They gain haste. At the beginning of the next end step, sacrifice them.
        ability.addMode(new Mode(new VenserFerventForgerEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT)));
        this.addAbility(ability);
    }

    private VenserFerventForger(final VenserFerventForger card) {
        super(card);
    }

    @Override
    public VenserFerventForger copy() {
        return new VenserFerventForger(this);
    }
}

class VenserFerventForgerEffect extends OneShotEffect {

    VenserFerventForgerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create two tokens that are copies of target permanent an opponent controls. " +
                "They gain haste. At the beginning of the next end step, sacrifice them";
    }

    private VenserFerventForgerEffect(final VenserFerventForgerEffect effect) {
        super(effect);
    }

    @Override
    public VenserFerventForgerEffect copy() {
        return new VenserFerventForgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, false, 2);
        effect.apply(game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game)), source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
