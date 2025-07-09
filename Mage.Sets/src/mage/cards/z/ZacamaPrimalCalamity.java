package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ZacamaPrimalCalamity extends CardImpl {

    public ZacamaPrimalCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Zacama, Primal Calamity enters the battlefield, if you cast it, untap all lands you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapAllLandsControllerEffect())
                .withInterveningIf(CastFromEverywhereSourceCondition.instance));

        // {2}{R}: Zacama deals 3 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{G}: Destroy target artifact or enchantment.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // {2}{W}: You gain 3 life.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(3), new ManaCostsImpl<>("{2}{W}")));
    }

    private ZacamaPrimalCalamity(final ZacamaPrimalCalamity card) {
        super(card);
    }

    @Override
    public ZacamaPrimalCalamity copy() {
        return new ZacamaPrimalCalamity(this);
    }
}
