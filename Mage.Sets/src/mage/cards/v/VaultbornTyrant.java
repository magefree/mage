package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VaultbornTyrant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");
    private static final FilterPermanent filter2 = new FilterPermanent("it's not a token");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
        filter2.add(TokenPredicate.FALSE);
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter2);

    public VaultbornTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Vaultborn Tyrant or another creature with power 4 or greater you control enters, you gain 3 life and draw a card.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainLifeEffect(3), filter, false, false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // When Vaultborn Tyrant dies, if it's not a token, create a token that's a copy of it, except it's an artifact in addition to its other types.
        this.addAbility(new DiesSourceTriggeredAbility(new VaultbornTyrantCreateCopyEffect()).withInterveningIf(condition));
    }

    private VaultbornTyrant(final VaultbornTyrant card) {
        super(card);
    }

    @Override
    public VaultbornTyrant copy() {
        return new VaultbornTyrant(this);
    }
}

class VaultbornTyrantCreateCopyEffect extends OneShotEffect {

    VaultbornTyrantCreateCopyEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a token that's a copy of it, except it's an artifact in addition to its other types";
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
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && new CreateTokenCopyTargetEffect(
                source.getControllerId(), CardType.ARTIFACT, false
        ).setSavedPermanent(permanent).apply(game, source);
    }
}
