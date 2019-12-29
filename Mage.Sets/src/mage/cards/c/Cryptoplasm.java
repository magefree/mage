
package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Cryptoplasm extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Cryptoplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CryptoplasmEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private Cryptoplasm(final Cryptoplasm card) {
        super(card);
    }

    @Override
    public Cryptoplasm copy() {
        return new Cryptoplasm(this);
    }

}

class CryptoplasmEffect extends OneShotEffect {

    CryptoplasmEffect() {
        super(Outcome.Copy);
        this.staticText = "you may have {this} become a copy of another target creature, except it gains this ability";
    }

    private CryptoplasmEffect(final CryptoplasmEffect effect) {
        super(effect);
    }

    @Override
    public CryptoplasmEffect copy() {
        return new CryptoplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, final Ability source) {
        Permanent creatureToCopy = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creatureToCopy != null) {
            ApplyToPermanent applier = new ApplyToPermanent() {
                @Override
                public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
                    Ability upkeepAbility = new BeginningOfUpkeepTriggeredAbility(new CryptoplasmEffect(), TargetController.YOU, true);
                    upkeepAbility.addTarget(new TargetCreaturePermanent());
                    permanent.addAbility(upkeepAbility, source.getSourceId(), game);
                    return true;
                }

                @Override
                public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
                    Ability upkeepAbility = new BeginningOfUpkeepTriggeredAbility(new CryptoplasmEffect(), TargetController.YOU, true);
                    upkeepAbility.addTarget(new TargetCreaturePermanent());
                    mageObject.getAbilities().add(upkeepAbility);
                    return true;
                }

            };
            game.copyPermanent(creatureToCopy, source.getSourceId(), source, applier);
        }
        return true;
    }
}
