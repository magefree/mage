package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VaultbornTyrant extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public VaultbornTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Vaultborn Tyrant or another creature with power 4 or greater enters the battlefield under your control, you gain 3 life and draw a card.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainLifeEffect(3), filter, false, true
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // When Vaultborn Tyrant dies, if it's not a token, create a token that's a copy of it, except it's an artifact in addition to its other types.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(
                        new VaultbornTyrantCreateCopyEffect(),
                        false
                ),
                VaultbornTyrant::checkSource,
                "When {this} dies, if it's not a token, create a token that's a copy of it, "
                        + "except it's an artifact in addition to its other types."
        ));
    }

    private VaultbornTyrant(final VaultbornTyrant card) {
        super(card);
    }

    @Override
    public VaultbornTyrant copy() {
        return new VaultbornTyrant(this);
    }

    static boolean checkSource(Game game, Ability source) {
        return !(source.getSourcePermanentOrLKI(game) instanceof PermanentToken);
    }
}

class VaultbornTyrantCreateCopyEffect extends OneShotEffect {

    VaultbornTyrantCreateCopyEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private VaultbornTyrantCreateCopyEffect(final VaultbornTyrantCreateCopyEffect effect) {
        super(effect);
    }

    @Override
    public VaultbornTyrantCreateCopyEffect copy() {
        return new VaultbornTyrantCreateCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), CardType.ARTIFACT, false
        );
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        return effect.apply(game, source);
    }
}
