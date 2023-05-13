package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterOpponent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyaraWidowOfTheRealm extends CardImpl {

    private static final DynamicValue xValue = new SacrificeCostConvertedMana("permanent");
    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer(
            "opponent or battle", StaticFilters.FILTER_PERMANENT_BATTLE, new FilterOpponent()
    );

    public AyaraWidowOfTheRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.a.AyaraFurnaceQueen.class;

        // {T}, Sacrifice another creature or artifact: Ayara, Widow of the Realm deals X damage to target opponent or battle and you gain X life, where X is the sacrificed permanent's mana value.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to target opponent or battle"), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        ability.addEffect(new GainLifeEffect(xValue).setText("and you gain X life, where X is the sacrificed permanent's mana value"));
        ability.addTarget(new TargetPermanentOrPlayer(filter));
        this.addAbility(ability);

        // {5}{R/P}: Transform Ayara. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{R/P}")));
    }

    private AyaraWidowOfTheRealm(final AyaraWidowOfTheRealm card) {
        super(card);
    }

    @Override
    public AyaraWidowOfTheRealm copy() {
        return new AyaraWidowOfTheRealm(this);
    }
}
