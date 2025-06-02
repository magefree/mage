package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class HraesvelgrOfTheFirstBrood extends CardImpl {

    public HraesvelgrOfTheFirstBrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Shiva's Aid -- When Hraesvelgr enters and whenever you cast a noncreature spell, target creature gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new OrTriggeredAbility(
                Zone.BATTLEFIELD, new BoostTargetEffect(1, 0),
                new EntersBattlefieldTriggeredAbility(null),
                new SpellCastControllerTriggeredAbility(null, StaticFilters.FILTER_SPELL_A_NON_CREATURE, false)
        );
        ability.addEffect(new CantBeBlockedTargetEffect().setText("can't be blocked this turn").concatBy("and"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Shiva's Aid"));
    }

    private HraesvelgrOfTheFirstBrood(final HraesvelgrOfTheFirstBrood card) {
        super(card);
    }

    @Override
    public HraesvelgrOfTheFirstBrood copy() {
        return new HraesvelgrOfTheFirstBrood(this);
    }
}
