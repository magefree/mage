package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EldraziDisplacer extends CardImpl {

    public EldraziDisplacer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {2}{C}: Exile another target creature, then return it to the battlefield tapped under its owner's control.
        Ability ability = new SimpleActivatedAbility(new ExileThenReturnTargetEffect(false, false, PutCards.BATTLEFIELD_TAPPED), new ManaCostsImpl<>("{2}{C}"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private EldraziDisplacer(final EldraziDisplacer card) {
        super(card);
    }

    @Override
    public EldraziDisplacer copy() {
        return new EldraziDisplacer(this);
    }
}
