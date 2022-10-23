package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustAttackOpponentWithCreatureTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DulcetSirens extends CardImpl {

    public DulcetSirens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.SIREN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {U}, {T}: Target creature attacks target opponent this turn if able.
        Ability ability = new SimpleActivatedAbility(
                new MustAttackOpponentWithCreatureTargetEffect(), new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Morph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{U}")));
    }

    private DulcetSirens(final DulcetSirens card) {
        super(card);
    }

    @Override
    public DulcetSirens copy() {
        return new DulcetSirens(this);
    }
}
