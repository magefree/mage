package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.BearToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArgothSanctumOfNature extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a legendary green creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public ArgothSanctumOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.meldsWithClazz = mage.cards.t.TitaniaVoiceOfGaea.class;
        this.meldsToClazz = mage.cards.h.HanweirTheWrithingTownship.class;

        // Argoth, Sanctum of Nature enters the battlefield tapped unless you control a legendary green creature.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {2}{G}{G}, {T}: Create a 2/2 green Bear creature token, then mill three cards. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new BearToken()), new ManaCostsImpl<>("{2}{G}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new MillCardsControllerEffect(3).concatBy(", then"));
        this.addAbility(ability);

        // (Melds with Titania, Voice of Gaea.)
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("<i>(Melds with Titania, Voice of Gaea.)</i>")
        ));
    }

    private ArgothSanctumOfNature(final ArgothSanctumOfNature card) {
        super(card);
    }

    @Override
    public ArgothSanctumOfNature copy() {
        return new ArgothSanctumOfNature(this);
    }
}
