package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class UltronArtificialMalevolence extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("another nontoken artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public UltronArtificialMalevolence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever another nontoken artifact you control enters, you may pay {2}. If you do, create a token that's a
        // copy of it. If the token isn't a creature, it becomes a 2/2 Robot Villain creature in addition to its other types.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            Zone.BATTLEFIELD,
            new DoIfCostPaid(
                new UltronArtificialMaleveolenceEffect(),
                new ManaCostsImpl<>("{2}")
            ),
            filter,
            false,
            SetTargetPointer.PERMANENT
        ));
    }

    private UltronArtificialMalevolence(final UltronArtificialMalevolence card) {
        super(card);
    }

    @Override
    public UltronArtificialMalevolence copy() {
        return new UltronArtificialMalevolence(this);
    }
}

class UltronArtificialMaleveolenceEffect extends OneShotEffect {

    public UltronArtificialMaleveolenceEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a token that's a copy of it. If the token isn't a creature, "
            + "it becomes a 2/2 Robot Villain creature in addition to its other types";
    }

    private UltronArtificialMaleveolenceEffect(final UltronArtificialMaleveolenceEffect effect) {
        super(effect);
    }

    @Override
    public UltronArtificialMaleveolenceEffect copy() {
        return new UltronArtificialMaleveolenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            if (!permanent.isCreature(game)) {
                CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), CardType.CREATURE, false);
                effect.withAdditionalSubType(SubType.ROBOT);
                effect.withAdditionalSubType(SubType.VILLAIN);
                effect.setPower(2);
                effect.setToughness(2);
                return effect.apply(game, source);
            } else {
                CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId());
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
