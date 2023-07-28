package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.AttachedPermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class LothlorienBlade extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
    private static final FilterControlledCreaturePermanent filterElf = new FilterControlledCreaturePermanent(SubType.ELF, "Elf");

    static {
        filter.add(LothlorienBladePredicate.instance);
    }

    public LothlorienBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, it deals damage equal to its power to target creature defending player controls.
        Ability ability = new AttacksAttachedTriggeredAbility(new DamageTargetEffect(AttachedPermanentPowerCount.instance)
                .setText("it deals damage equal to its power to target creature defending player controls"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Equip Elf {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), new TargetControlledCreaturePermanent(filterElf), false));

        // Equip {5}
        this.addAbility(new EquipAbility(5, false));

    }

    private LothlorienBlade(final LothlorienBlade card) {
        super(card);
    }

    @Override
    public LothlorienBlade copy() {
        return new LothlorienBlade(this);
    }
}

enum LothlorienBladePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional.ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                .map(input.getObject()::isControlledBy)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "";
    }
}
