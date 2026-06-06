package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HanweirBattlements extends MeldCard {

    public HanweirBattlements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Hanweir, the Writhing Township",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.OOZE}, "");

        this.meldsWithClazz = mage.cards.h.HanweirGarrison.class;

        // Hanweir Battlements
        // {T}: Add {C}.
        this.getLeftHalfCard().addAbility(new ColorlessManaAbility());

        // {R}, {T}: Target creature gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // {3}{R}{R}, {T}: If you both own and control Hanweir Battlements and a creature named Hanweir Garrison, exile them, then meld them into Hanweir, the Writhing Township.
        ability = new SimpleActivatedAbility(
                new MeldEffect("Hanweir Garrison", "Hanweir, the Writhing Township")
                        .setText("if you both own and control {this} and a creature named Hanweir Garrison, " +
                                "exile them, then meld them into Hanweir, the Writhing Township"),
                new ManaCostsImpl<>("{3}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // Hanweir, the Writhing Township
        this.getRightHalfCard().setPT(7, 4);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever Hanweir, the Writhing Township attacks, create two 3/2 colorless Eldrazi Horror creature tokens tapped and attacking.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(
                new EldraziHorrorToken(), 2, true, true
        ), false));
    }

    private HanweirBattlements(final HanweirBattlements card) {
        super(card);
    }

    @Override
    public HanweirBattlements copy() {
        return new HanweirBattlements(this);
    }
}
