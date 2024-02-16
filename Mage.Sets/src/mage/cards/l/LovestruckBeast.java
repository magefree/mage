package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LovestruckBeast extends AdventureCard {

    public LovestruckBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{G}", "Heart's Desire", "{G}");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Lovestruck Beast can't attack unless you control a 1/1 creature.
        this.addAbility(new SimpleStaticAbility(new LovestruckBeastEffect()));

        // Heart's Desire
        // Create a 1/1 white Human creature token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new HumanToken()));

        this.finalizeAdventure();
    }

    private LovestruckBeast(final LovestruckBeast card) {
        super(card);
    }

    @Override
    public LovestruckBeast copy() {
        return new LovestruckBeast(this);
    }
}

class LovestruckBeastEffect extends RestrictionEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, 1));
    }

    LovestruckBeastEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control a 1/1 creature";
    }

    private LovestruckBeastEffect(final LovestruckBeastEffect effect) {
        super(effect);
    }

    @Override
    public LovestruckBeastEffect copy() {
        return new LovestruckBeastEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId())
                && game.getBattlefield().countAll(filter, source.getControllerId(), game) <= 0;
    }
}
