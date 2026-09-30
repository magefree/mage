package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

public final class UugguuTheOmniplasm extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("another nontoken Ooze creature you control");

    static {
        filter.add(SubType.OOZE.getPredicate());
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
    }

    public UugguuTheOmniplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another nontoken Ooze creature you control dies, create two tokens that are
        // copies of that creature, except they're 2/2 and they aren't legendary.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new UugguuTheOmniplasmEffect(), false, filter, true
        ));
    }

    private UugguuTheOmniplasm(final UugguuTheOmniplasm card) {
        super(card);
    }

    @Override
    public UugguuTheOmniplasm copy() {
        return new UugguuTheOmniplasm(this);
    }
}

class UugguuTheOmniplasmEffect extends OneShotEffect {

    UugguuTheOmniplasmEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create two tokens that are copies of that creature, " +
                "except they're 2/2 and they aren't legendary";
    }

    private UugguuTheOmniplasmEffect(final UugguuTheOmniplasmEffect effect) {
        super(effect);
    }

    @Override
    public UugguuTheOmniplasmEffect copy() {
        return new UugguuTheOmniplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) getValue("creatureDied");
        if (creature == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, 2);
        effect.setSavedPermanent(creature);
        effect.setPower(2);
        effect.setToughness(2);
        effect.setIsntLegendary(true);
        return effect.apply(game, source);
    }
}
