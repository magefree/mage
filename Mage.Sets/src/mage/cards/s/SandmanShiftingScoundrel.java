package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandmanShiftingScoundrel extends CardImpl {

    public SandmanShiftingScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAND);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sandman's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance)));

        // Sandman can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // {3}{G}{G}: Return this card and target land card from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true)
                        .setText("return this card"),
                new ManaCostsImpl<>("{3}{G}{G}")
        );
        ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect(true)
                .setText("and target land card from your graveyard to the battlefield tapped"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND));
        this.addAbility(ability);
    }

    private SandmanShiftingScoundrel(final SandmanShiftingScoundrel card) {
        super(card);
    }

    @Override
    public SandmanShiftingScoundrel copy() {
        return new SandmanShiftingScoundrel(this);
    }
}
